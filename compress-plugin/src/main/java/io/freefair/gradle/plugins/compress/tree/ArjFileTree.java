package io.freefair.gradle.plugins.compress.tree;

import org.apache.commons.compress.archivers.arj.ArjArchiveEntry;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.gradle.api.internal.file.collections.DirectoryFileTreeFactory;
import org.gradle.internal.hash.FileHasher;
import org.gradle.internal.nativeintegration.filesystem.Chmod;

import java.io.File;

/**
 * @author Lars Grefer
 */
public class ArjFileTree extends ArchiveFileTree<ArjArchiveInputStream, ArjArchiveEntry> {

    public ArjFileTree(File archiveFile, ArchiveInputStreamProvider<ArjArchiveInputStream> inputStreamProvider, File tmpDir, Chmod chmod, DirectoryFileTreeFactory directoryFileTreeFactory, FileHasher fileHasher) {
        super(archiveFile, inputStreamProvider, tmpDir, chmod, directoryFileTreeFactory, fileHasher);
    }

    @Override
    ArchiveEntryFileTreeElement createDetails(Chmod chmod) {
        return new ArjArchiveEntryFileTreeElement(chmod);
    }

    public class ArjArchiveEntryFileTreeElement extends ArchiveEntryFileTreeElement {

        ArjArchiveEntryFileTreeElement(Chmod chmod) {
            super(chmod);
        }

        public int getMode() {
            int unixMode = getArchiveEntry().getUnixMode() & 0777;
            if (unixMode == 0) {
                return super.getMode();
            }
            return unixMode;
        }
    }


}
