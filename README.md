
## Package native

```
mvn  clean package -Pnative
```

## DEploy OCP 4

```
oc:build oc:resource oc:apply -Popenshift
```

## Schema de Banco de dados

```
oc new-app --template=mysql-persistent \
-p MYSQL_USER=admin \
-p MYSQL_PASSWORD=admin \
-p MYSQL_DATABASE=pessoadb \
-p MYSQL_ROOT_PASSWORD=root  \
-p MYSQL_VERSION=8.0-el8 \
-p VOLUME_CAPACITY=1Gi \
-p MEMORY_LIMIT=512Mi
```