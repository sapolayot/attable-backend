### Step Deploy

```

mvn clean install -DskipTests
# test-staging
docker build -t artisandigitalasia/attable-backend:test-staging .
docker push artisandigitalasia/attable-backend:test-staging

# staging
docker build -t artisandigitalasia/attable-backend:staging .
docker push artisandigitalasia/attable-backend:staging

Host
# test-staging
docker pull artisandigitalasia/attable-backend:test-staging
docker-compose -f back-test1.yml up --build --force-recreate -d

# staging
docker pull artisandigitalasia/attable-backend:staging
docker-compose -f backend-staging.yml up --build --force-recreate -d

```