WORKING_DIR=$(cd "$(dirname "$0")" && pwd)
export WORKING_DIR
echo $WORKING_DIR
echo "Start Building........."
cd "$WORKING_DIR"/assignment-core && mvn clean install
cd ../
echo "End Building........."
echo "Start Building........."
cd "$WORKING_DIR"/assignment-auth && mvn clean install
cd ../
echo "End Building........."
echo "Start Building........."
cd "$WORKING_DIR"/assignment-discovery && mvn clean install
cd ../
echo "End Building........."
echo "Start Building........."
cd "$WORKING_DIR"/assignment-gateway && mvn clean install
cd ../
echo "End Building........."
echo "Start Building........."
cd "$WORKING_DIR"/assignment-task-manager && mvn clean install
cd ../
echo "End Building........."
