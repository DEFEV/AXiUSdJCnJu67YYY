
#!/bin/bash

# author: Adán Escobar
# mail: adan@codeits.cl
# linkedin: https://www.linkedin.com/in/aescobar-ing-civil-computacion/

cd ..
DIR_BASE=$(pwd)
export BERMANN_COMMONS_BRANCH="feature/20250109_IACD"

export GROUPID="com.example"
export ARTIFACTID="apptrigger"
export PACKAGING=jar
export MAIN_CLASS="org.springframework.boot.loader.JarLauncher"

cd $ARTIFACTID
source .code-version
CURRENT_BRANCH="$(git branch --show-current)"
CURRENT_BRANCH_HASH="$(git rev-parse --short HEAD)"
export VERSION="$CODE_VERSION-$CURRENT_BRANCH-$CURRENT_BRANCH_HASH"
ARTIFACT="$ARTIFACTID-$VERSION.$PACKAGING"

#export JAVA_EXEC="/usr/lib/jvm/java-11-openjdk-11.0.18.0.10-2.el8_7.x86_64/bin/java"

#define default arguments
CANTIDAD=200
SEGMENTO=-1


#define options hashmap to override default arguments values
declare -A app_options
app_options=( 
	["--cantidad"]="CANTIDAD" # <-- define here your option jar arguments
	["--segment"]="SEGMENTO" # <-- define here your option jar arguments
)

#declare jar arguments setter
config_app() {
	APP_ARGS=($CANTIDAD $SEGMENTO) # <-- put here your jar arguments
	echo "appTrigger APP_ARGS=${APP_ARGS[@]}"
}
