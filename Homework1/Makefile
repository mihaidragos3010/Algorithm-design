# Exemplu de Makefile pentru soluții scrise în Java.

.PHONY: build clean

build: Feribot.class Nostory.class Sushi.class Semnale.class Badgpt.class

run-p1:
	java Feribot

run-p2:
	java Nostory

run-p3:
	java Sushi

run-p4:
	java Semnale

run-p5:
	java Badgpt

# Nu uitați să modificați numele surselor.
Feribot.class:
	javac Feribot.java

Nostory.class:
	javac Nostory.java

Semnale.class:
	javac Semnale.java

Sushi.class:
	javac Sushi.java

Badgpt.class:
	javac Badgpt.java

# Vom șterge fișierele bytecode compilate.
clean:
	rm -f *.class