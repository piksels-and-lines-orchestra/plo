/*
 * Copyright (c) 2010 Øyvind Kolås <pippin@gimp.org>
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

#include <lyd/lyd.h>
#include <unistd.h>
#include <stdlib.h>
#include <sndfile.h>
#include <glib.h>

#include "http/mongoose.h"
#include "http/mongoose.c"
#include "http/sha1.h"
#include "http/sha1.c"

static int wave_handler (Lyd *lyd, const char *wavename, void *user_data);

static int argc;
static char **argv;
static Lyd *lyd;

static void *http_handler(enum mg_event event,
                          struct mg_connection *conn,
                          const struct mg_request_info *request_info)
{
  unsigned char output[80];
  sha1_state state;
  sha1_init (&state);
  printf ("%s\n", request_info->uri);
  sha1_process (&state, request_info->uri, strlen (request_info->uri));
  sha1_done (&state,  output);

//  printf ("[%s]\n", output);
        {
          const char *wavpath = argv[(output[0]%(argc-1)) + 1];
          //const char *wavpath = argv[1];
          LydProgram *instrument;
          LydVoice   *voice;
          char        code[1024];

#define MIDDLE_C 261.6235565 
          switch(output[1]%9)
            {
              case 0: /* plain playback */
                sprintf (code, "wave('%s') * volume", wavpath);
                break;
              case 1:
                sprintf (code, "wave('%s', %f) * volume", wavpath, MIDDLE_C*1.9);
                break;
              case 2:
                sprintf (code, "wave('%s', %f) * volume", wavpath, MIDDLE_C*4.0);
                break;
              case 3:
                sprintf (code, "wave('%s', 261.6235565 + sin(4) * 10) * volume", wavpath);
                break;
              case 4: /* 150% speed */
                sprintf (code, "wave('%s', %f) * volume", wavpath, MIDDLE_C*1.5);
                break;
              case 5: /* 66.67% speed */
                sprintf (code, "wave('%s', %f) * volume", wavpath, MIDDLE_C * 0.666);
                break;
              case 7: /* tremolo */
                sprintf (code, "wave('%s', 261.6235565 + sin(5) * 10) * volume", wavpath);
                break;
              case 8: /* vibrato */
                sprintf (code, "wave('%s') * sin(4) * 2 * volume", wavpath);
                break;
              /* with band pass*/
            }

          printf ("{%s} %i\n", code, 
          output[1]%9
              
              );

          if (code[0]==0)
            return "procesed";

          instrument = lyd_compile (lyd, code);
          voice = lyd_voice_new (lyd, instrument, 0.0, 0);

          /* any variable (that is non-reserved keyword) in the source can be manipulated
           * in realtime like this: 
           */
          lyd_voice_set_param (lyd, voice, "volume", 1.5 * (rand()%511)/511.0 + 0.4);

          //lyd_voice_set_delay (lyd, voice, (rand()%511)/511.0);

          /* for fire and forget voices, it is nice to set a time to live before release*/
          lyd_voice_set_duration (lyd, voice, 3.0);

          lyd_voice_set_position (lyd, voice, 2 * (rand()%511)/511.0 - 1);

          lyd_program_free (instrument);
        }

  return "procesed";
}

int main (int    targc,
          char **targv)
{
  lyd = lyd_new ();
  int  i;

  argc = targc;
  argv = targv;

  const char *options[]={"listening_ports", "6150", NULL};

  if (!argv[1])
    {
      printf ("Usage:\n");
      printf (" %s file.wav [file2.wav ..]\n", argv[0]);
      return -1;
    }

  if (!lyd_audio_init (lyd, "auto"))
    {
      lyd_free (lyd);
      printf ("failed to initialize lyd (audio output)\n");
      return -1;
    }

  lyd_set_voice_count (lyd, 10);
  lyd_set_wave_handler (lyd, wave_handler, NULL);

  mg_start (http_handler, NULL, options);
  pause ();
  return 0;
  if (ssl_error ())
    return -1;
  lyd_free (lyd);
  return 0;
}

/* callback to load file on demand when compiling LydPrograms
 */
static int
wave_handler (Lyd *lyd, const char *wavename, void *user_data)
{
  SNDFILE *infile;
  SF_INFO  sfinfo;

  sfinfo.format = 0;
  if (!(infile = sf_open (wavename, SFM_READ, &sfinfo)))
    {
      float data[10];
      lyd_load_wave (lyd, wavename, 10, 400, data);
      printf ("failed to open file %s\n", wavename);
      sf_perror (NULL);
      return 1;
    }

  if (sfinfo.channels > 1)
    {
      printf ("too many channels\n");
      return 1;
    }
  {
    float *data = malloc (sfinfo.frames * sizeof (float));
    sf_read_float (infile, data, sfinfo.frames);
    lyd_load_wave (lyd, wavename, sfinfo.frames, sfinfo.samplerate, data);
    free (data);
    sf_close (infile);
    printf ("loaded %s\n", wavename);
  }
  return 0;
}
