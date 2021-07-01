.PHONY: run
run:
	java -jar desktop/build/libs/desktop-1.0.jar

.PHONY: run_gradle_windows
run_gradle_windows:
	gradlew desktop:run

.PHONY: run_gradle_linux
run_gradle_linux:
	./gradlew desktop:run