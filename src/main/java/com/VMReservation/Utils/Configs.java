package com.VMReservation.Utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/main/resources/config.properties"
})
public interface Configs extends Config {

    @DefaultValue("100")
    int maxPoolSize();
}
