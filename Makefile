.PHONY: build

GRADLE = ./gradlew

build:
	echo " --- BUILDING API --- "
	${GRADLE} clean build -x test

test:
	echo " --- TESTING API --- "
	${GRADLE} test
