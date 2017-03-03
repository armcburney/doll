NAME = "Main"
JFLAGS = -cp -g -d .

all:
	@echo "Compiling..."
	javac $(JFLAGS) src/main/java/*.java

run: all
	@echo "Running..."
	java -cp $(JARS):. ca.andrewmcburney.cs349.a3.Main

clean:
	rm -rf *.class
	rm -rf files/*.json
