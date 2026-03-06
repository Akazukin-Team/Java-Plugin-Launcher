package org.akazukin.app.loader;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.akazukin.loader.Loader;
import org.akazukin.loader.LoaderConfig;
import org.akazukin.loader.api.ILoader;
import org.akazukin.loader.api.ILoaderConfig;
import org.akazukin.loader.api.context.IPluginContext;

import java.util.Scanner;

public class Launcher {
    @Setter
    @Getter
    boolean running;

    @SneakyThrows
    public void run() {
        final ILoaderConfig cfg = LoaderConfig.builder()
                .setLoadFromClassPath(true)
                .setPluginDirectories(new String[]{"plugins"})
                .build();
        try (final ILoader loader = new Loader(cfg)) {
            loader.getPluginMgr().enableAll();
            for (final IPluginContext ctx : loader.getPluginResolver().getAllPlugins()) {
                System.out.println(ctx.getMetadata().getId() + " : " + ctx.getState().getName());
            }

            this.running = true;
            final Scanner scanner = new Scanner(System.in);
            while (this.running) {
                if (scanner.hasNextLine()) {
                    final String line = scanner.nextLine();
                    if (line.equalsIgnoreCase("exit")) {
                        this.running = false;
                    }
                }
            }
        }
    }
}
