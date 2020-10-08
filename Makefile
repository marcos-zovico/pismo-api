.PHONY: build

GRADLE = ./gradlew --stacktrace --no-daemon --console=plain

build:
	echo " --- BUILDING API --- "
	${GRADLE} clean build -x test

test:
	echo " --- TESTING API --- "
	${GRADLE} test
