# Exemplu de Makefile pentru soluții scrise în Java.

.PHONY: build clean

build: Supercomputer.class Magazin.class Ferate.class

run-p1:
	java Supercomputer

run-p4:
	java Magazin

run-p2:
	java Ferate


# Nu uitați să modificați numele surselor.
Supercomputer.class:
	javac Supercomputer.java

Magazin.class:
	javac Magazin.java

Ferate.class:
	javac Ferate.java

# Vom șterge fișierele bytecode compilate.
clean:
	rm -f *.class