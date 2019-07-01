JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Car.java \
	CarOwner.java \
	MySqlDatabase.java \
	Project.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class