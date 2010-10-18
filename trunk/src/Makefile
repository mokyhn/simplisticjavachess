JFLAGS = -g
JC = javac
MYCLASSDIR = ../class/.

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) -d $(MYCLASSDIR) $*.java

CLASSES = \
	Move.java \
	Piece.java \
	Board.java \
	Evaluator.java \
	Movegenerator.java \
	Search.java \
	main.java

default: classes 

classes: $(CLASSES:.java=.class)

exec:
	gcj -c -g -O $(MYCLASSDIR) *.java
	gcj -B $(MYCLASSDIR) --main=main -o main *.o

clean:
	$(RM) ../class/*.class

