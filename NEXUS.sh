source ./base.sh

export NEXUS_REPO_JAVA_APPS=bermann-java-apps
export NEXUS_SERVER=http://192.168.101.51:8081


export NEXUS_USR=nexus-publisher
export NEXUS_PSW="Ts34nq74.,12"

bash jar-make.sh --publish
