/* LibTomCrypt, modular cryptographic library -- Tom St Denis
 *
 * LibTomCrypt is a library that provides various cryptographic
 * algorithms in a highly modular and flexible manner.
 *
 * The library is free for all purposes without any express
 * guarantee it works.
 *
 * Tom St Denis, tomstdenis@gmail.com, http://libtom.org
 *
 * The plain ANSIC sha1 functionality has been extracted from libtomcrypt,
 * and is included directly in the sources. /Øyvind K. - since libtomcrypt
 * is public domain the adaptations done here to make the sha1 self contained
 * also is public domain.
 */

#include <inttypes.h>

#ifndef __SHA1_H
#define __SHA1_H

typedef struct sha1_state {
    uint64_t length;
    uint32_t state[5], curlen;
    unsigned char buf[64];
} sha1_state;
int sha1_init(sha1_state * sha1);
int sha1_process(sha1_state *sha1, const unsigned char * msg, unsigned long len);
int sha1_done(sha1_state * sha1, unsigned char *out);

#endif
