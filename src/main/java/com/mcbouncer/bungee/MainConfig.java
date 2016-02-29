package com.mcbouncer.bungee;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;

// Most of the code in this class is based off of https://github.com/KittehOrg/SQLBans/blob/master/src/main/java/org/kitteh/sqlbans/Config.java

@SuppressWarnings("unchecked")
public class MainConfig {

    private Map<String, Object> map = new LinkedHashMap<String, Object>();

    private MCBouncerBungeeCord plugin;

    public MainConfig(MCBouncerBungeeCord plugin) {
        this.plugin = plugin;
        final File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                InputStream is = plugin.getResourceAsStream("config.yml");
                File dataFolder = plugin.getDataFolder();
                dataFolder.mkdirs();
                OutputStream os = new FileOutputStream(configFile);
                final byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
            } catch (final Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save default config", e);
            }
        }
        try {
            this.map = (Map<String, Object>) new Yaml().load(new FileInputStream(configFile));
            this.apikey = getString("apikey", this.apikey);
            if (this.apikey.equalsIgnoreCase("REPLACE")) {
                plugin.getLogger().log(Level.SEVERE, "APIKey is set to the default, please set the apikey in the plugin config file");
            }
            this.defaultKickMessage = getString("defaultKickMessage", this.defaultKickMessage);
            this.numBansDisallow = getInt("numBansDisallow", this.numBansDisallow);
            this.showBanMessages = getBoolean("showBanMessages");
        } catch (final Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load config!", e);
        }
    }

    public String apikey = "REPLACE";

    public String defaultKickMessage = "Kicked by an admin.";

    public String defaultBanMessage = "Banned for rule violation.";

    public Integer numBansDisallow = -1;

    public Boolean showBanMessages = false;



    Object get(String string) {
        try {
            return this.get(string, this.map);
        } catch (final Exception e) {
            return null;
        }
    }

    int getInt(String string) {
        return getInt(string, 0);
    }

    int getInt(String string, Integer def) {
        try {
            final String s = this.get(string).toString();
            try {
                return Integer.parseInt(s);
            } catch (final NumberFormatException e) {
                return def;
            }
        } catch (final Exception e) {
            return def;
        }
    }

    String getString(String string) {
        final Object o = this.get(string);
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    String getString(String string, String def) {
        final String result = this.getString(string);
        if (result == null) {
            return def;
        }
        return result;
    }

    Boolean getBoolean(String string) {
        final String s = this.get(string).toString();
        return Boolean.parseBoolean(s);
    }

    private Object get(String string, Map<String, Object> map) {
        if (string.contains(".") && (string.indexOf(".") != (string.length() - 1))) {
            final Object o = map.get(string.substring(0, string.indexOf(".")));
            if (o instanceof Map) {
                return this.get(string.substring(string.indexOf(".") + 1), (Map<String, Object>) o);
            } else {
                return null;
            }
        }
        return map.get(string);
    }

}
