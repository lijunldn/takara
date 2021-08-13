# Project Takara
- - - - 

Project Takara is a data cache application. 

## Gemis ##

    In order to start a Takara Cache, pass in one of the enum types defined in 'me.takara.shared.Entity'.

## Shared ##
    
    Client side module for applications who want to access Takara data. 

## GemisClient ##

    Sample client application that queries data from Takara data cache.  

```java
    @Test
    public void testGetDataByItemID() {
        TakaraRepository repository = TakaraBuilder.create(Entity.BOND);
        Instrument data = repository.get(1);
        ...
    

