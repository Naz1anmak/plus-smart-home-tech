#!/bin/zsh

# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "${GREEN}🚀 Запуск микросервисов Smart Home Tech${NC}"
echo ""

# Базовая директория
PROJECT_DIR="/Users/alexander/Java/plus-smart-home-tech"
cd "$PROJECT_DIR"

# Функция для проверки доступности порта
wait_for_port() {
    local port=$1
    local service=$2
    local max_wait=60
    local elapsed=0

    echo "${YELLOW}⏳ Ожидание запуска $service на порту $port...${NC}"

    while ! nc -z localhost $port 2>/dev/null; do
        sleep 2
        elapsed=$((elapsed + 2))
        if [ $elapsed -ge $max_wait ]; then
            echo "${RED}❌ Таймаут ожидания $service${NC}"
            return 1
        fi
    done

    echo "${GREEN}✅ $service запущен${NC}"
    return 0
}

# 1. Запуск Eureka Discovery Server
echo "${YELLOW}1️⃣  Запуск Eureka Discovery Server...${NC}"
java -jar infra/discovery-server/target/discovery-server-1.0-SNAPSHOT.jar > logs/eureka.log 2>&1 &
EUREKA_PID=$!
echo "PID: $EUREKA_PID"

wait_for_port 8761 "Eureka" || exit 1
sleep 5

# 2. Запуск Config Server
echo ""
echo "${YELLOW}2️⃣  Запуск Config Server...${NC}"
java -jar infra/config-server/target/config-server-1.0-SNAPSHOT.jar > logs/config-server.log 2>&1 &
CONFIG_PID=$!
echo "PID: $CONFIG_PID"

echo "${YELLOW}⏳ Ожидание регистрации Config Server в Eureka (10 секунд)...${NC}"
sleep 10

# 3. Запуск Warehouse
echo ""
echo "${YELLOW}3️⃣  Запуск Warehouse Service...${NC}"
java -jar commerce/warehouse/target/warehouse-1.0-SNAPSHOT.jar > logs/warehouse.log 2>&1 &
WAREHOUSE_PID=$!
echo "PID: $WAREHOUSE_PID"

echo "${YELLOW}⏳ Ожидание регистрации Warehouse в Eureka (15 секунд)...${NC}"
sleep 15

# 4. Запуск Shopping Cart
echo ""
echo "${YELLOW}4️⃣  Запуск Shopping Cart Service...${NC}"
java -jar commerce/shopping-cart/target/shopping-cart-1.0-SNAPSHOT.jar > logs/shopping-cart.log 2>&1 &
CART_PID=$!
echo "PID: $CART_PID"

echo "${YELLOW}⏳ Ожидание регистрации Shopping Cart в Eureka (15 секунд)...${NC}"
sleep 15

# 5. Запуск Shopping Store
echo ""
echo "${YELLOW}5️⃣  Запуск Shopping Store Service...${NC}"
java -jar commerce/shopping-store/target/shopping-store-1.0-SNAPSHOT.jar > logs/shopping-store.log 2>&1 &
STORE_PID=$!
echo "PID: $STORE_PID"

sleep 5

echo ""
echo "${GREEN}✅ Все сервисы запущены!${NC}"
echo ""
echo "PIDs:"
echo "  Eureka:         $EUREKA_PID"
echo "  Config Server:  $CONFIG_PID"
echo "  Warehouse:      $WAREHOUSE_PID"
echo "  Shopping Cart:  $CART_PID"
echo "  Shopping Store: $STORE_PID"
echo ""
echo "Для остановки всех сервисов выполните:"
echo "  kill $EUREKA_PID $CONFIG_PID $WAREHOUSE_PID $CART_PID $STORE_PID"
echo ""
echo "Eureka Dashboard: http://localhost:8761"



