#!/bin/bash

for f in `ls src/*.txt`; do
	asciidoctor -D gen -a linkcss! -a source-highlighter=coderay -a stylesheet=src/colony.css $f;
done
cp src/*.png gen

