.PHONY: build

GRADLE = ./gradlew
DOCKER_IMAGE_NAME = pismo-api
DOCKER_HUB_USER = mzovico

build:
	echo " --- BUILDING API --- "
	${GRADLE} clean build -x test

test:
	echo " --- TESTING API --- "
	${GRADLE} test

run:
	docker-compose up --build

stop:
	docker-compose down

docker-build:
	echo " --- BUILDING IMAGE --- "
	rm -R ./build/dependency
	mkdir build/dependency
	(cd build/dependency; jar -xf ../libs/*.jar)
	docker build -t ${DOCKER_IMAGE_NAME} .
	echo " --- TAGGING IMAGE --- "
	docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}

docker-push:
	echo " --- LOGGING IN --- "
	docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASS}
	echo " --- PUSHING IMAGE --- "
	docker push ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}
