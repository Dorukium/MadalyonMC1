/*
 * Hello Minecraft! Launcher
 * Copyright (C) 2025  huangyuhui <huanghongxun2008@126.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.madalyonmc.launcher.terracotta.provider;

import javafx.beans.value.ObservableValue;
import com.madalyonmc.launcher.task.Task;
import com.madalyonmc.launcher.util.tree.TarFileTree;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ITerracottaProvider {
    enum Status {
        NOT_EXIST,
        LEGACY_VERSION,
        READY
    }

    interface Context {
        void bindProgress(ObservableValue<? extends Number> value);

        boolean requestInstallFence();
        
        boolean hasInstallFence();
    }

    abstract class ProviderException extends IOException {
        public ProviderException(String message) {
            super(message);
        }

        public ProviderException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProviderException(Throwable cause) {
            super(cause);
        }
    }

    final class ArchiveFileMissingException extends ProviderException {
        public ArchiveFileMissingException(String message) {
            super(message);
        }

        public ArchiveFileMissingException(String message, Throwable cause) {
            super(message, cause);
        }

        public ArchiveFileMissingException(Throwable cause) {
            super(cause);
        }
    }

    Status status() throws IOException;

    Task<?> install(Context context, @Nullable TarFileTree tree) throws IOException;

    List<String> ofCommandLine(Path path);
}
