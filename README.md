# spring-cloud-config-vault-health

To initialize vault please use dev:
vault server -dev

You can add random properties to kv backend:
vault kv put secret/application test=true


This project:
1) Connects to vault back end
2) From a scheduled executor thread, calls health on all HealthIndicators every 30 seconds

Please notice that the vault repo health check fails from the scheduled executor thread

However, vault repo health check succeeds on regular actuator health endpoint
Please run to verify: curl localhost:8888/actuator/health



Am trying to understand why this is the case
Thanks for any help
