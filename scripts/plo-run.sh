APPLICATION=$1

if [ -z "$2" ]; then
    PREFIX="~/local/plo"
else
    PREFIX=$2
fi

export LD_LIBRARY_PATH=$PREFIX/lib:$LD_LIBRARY_PATH
export PATH=$PREFIX/bin:$PATH
exec $PREFIX/bin/$APPLICATION
