# Star Wars Api

Api desenvolvida para consulta às informações dos planetas de star wars.

Utilizando:
- Spring Boot
- MongoDB
- API pública do Star Wars:  https://swapi.co/
- Restful services
- Maven

## Como iniciar:
Execute o método main da classe com.challenge.starwarsapi.StarwarsApiApplication.java e automaticamente um tomcat será iniciado na porta:8080.

## Testes Unitários:
As classes abaixo estão configuradas para executar testes unitários:
- com.challenge.starwarsapi.planet.repository.PlanetRepositoryUTest.java
- com.challenge.starwarsapi.planet.repository.PlanetSequenceRepositoryUTest.java
- com.challenge.starwarsapi.planet.resources.PlanetResourcesUTest.java
- com.challenge.starwarsapi.planet.services.PlanetSWAPIServicesUTest.java
- com.challenge.starwarsapi.swapi.planet.services.PlanetServicesUTest.java

URLs: 
###### Listar planetas
- **GET** http://localhost:8080/starwars-api/planets

###### Incluir um planeta (Neste momente será acessada a API pública para verificação das aparições em filmes)
- **POST** http://localhost:8080/starwars-api/planets
```
{
    "name": "Tatooine", 
    "climate": "temperate",
    "terrain": "grasslands, mountains"
}
```

###### Encontrar um planeta pelo id
- **GET** http://localhost:8080/starwars-api/planets/1

###### Encontrar um planeta pelo nome
- **GET** http://localhost:8080/starwars-api/planets/name/Tatooine

###### Deletar um planeta
- **DELETE** http://localhost:8080/starwars-api/planets/1
