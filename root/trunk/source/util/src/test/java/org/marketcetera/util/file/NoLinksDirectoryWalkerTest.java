package org.marketcetera.util.file;

import java.io.File;
import java.util.Collection;
import java.util.Vector;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.marketcetera.util.misc.OperatingSystem;
import org.marketcetera.util.test.TestCaseBase;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.marketcetera.util.test.CollectionAssert.*;

public class NoLinksDirectoryWalkerTest
	extends TestCaseBase
{
    private static final String TEST_ROOT=
        DIR_ROOT+File.separator+"directory_walker"+File.separator;
    protected static final String TEST_NONEXISTENT_FILE=
        TEST_ROOT+"nonexistent";
    private static final String TEST_FILE=
        "a.txt";
    private static final String[] TEST_FILE_LIST=new String[] {
        "a.txt", "b.txt","c.txt","d.txt"};
    private static final String[] TEST_DIR_LIST=new String[] {
        "a","b","c","d","directory_walker"};


    public static final class ListWalker
        extends NoLinksDirectoryWalker
    {
        private Vector<String> mFiles=new Vector<String>();
        private Vector<String> mDirectories=new Vector<String>();
        private int mMaxDepth=-1;

        public String[] getFiles()
        {
            return mFiles.toArray(new String[0]);
        }

        public String[] getDirectories()
        {
            return mDirectories.toArray(new String[]{});
        }

        public int getMaxDepth()
        {
            return mMaxDepth;
        }

        @Override
        protected void handleDirectoryStart
            (File directory,
             int depth,
             Collection results)
        {
            mDirectories.add(directory.getName());
            if (results!=null) {
                results.add(directory.getName());
            }
            if (depth>mMaxDepth) {
                mMaxDepth=depth;
            }
        }

        @Override
        protected void handleFile
            (File file,
             int depth,
             Collection results)
        {
            mFiles.add(file.getName());
            if (results!=null) {
                results.add(file.getName());
            }
            if (depth>mMaxDepth) {
                mMaxDepth=depth;
            }
        }
    }

    @Test
    public void singleFile()
        throws Exception
    {
        ListWalker walker=new ListWalker();
        walker.apply(TEST_ROOT+TEST_FILE);
        assertArrayPermutation
            (new String[] {TEST_FILE},walker.getFiles());
        assertArrayPermutation
            (new String[0],walker.getDirectories());
        assertEquals(0,walker.getMaxDepth());

        Vector<String> results=new Vector<String>();
        walker=new ListWalker();
        walker.apply(TEST_ROOT+TEST_FILE,results);
        assertArrayPermutation
            (new String[] {TEST_FILE},walker.getFiles());
        assertArrayPermutation
            (new String[0],walker.getDirectories());
        assertArrayPermutation
            (new String[] {TEST_FILE},results.toArray(new String[0]));
        assertEquals(0,walker.getMaxDepth());
    }

    @Test
    public void nonexistentFiles()
        throws Exception
    {
        ListWalker walker=new ListWalker();
        walker.apply(TEST_NONEXISTENT_FILE);
        assertArrayPermutation(new String[0],walker.getFiles());
        assertArrayPermutation(new String[0],walker.getDirectories());
        assertEquals(-1,walker.getMaxDepth());

        Vector<String> results=new Vector<String>();
        walker=new ListWalker();
        walker.apply(TEST_NONEXISTENT_FILE,results);
        assertArrayPermutation(new String[0],walker.getFiles());
        assertArrayPermutation(new String[0],walker.getDirectories());
        assertArrayPermutation(new String[0],results.toArray(new String[0]));
        assertEquals(-1,walker.getMaxDepth());
    }

    @Test
    public void win32Walk()
        throws Exception
    {
        assumeTrue(OperatingSystem.LOCAL.isWin32());

        ListWalker walker=new ListWalker();
        walker.apply(TEST_ROOT);
        assertArrayPermutation(TEST_FILE_LIST,walker.getFiles());
        assertArrayPermutation(TEST_DIR_LIST,walker.getDirectories());
        assertEquals(3,walker.getMaxDepth());

        Vector<String> results=new Vector<String>();
        walker=new ListWalker();
        walker.apply(TEST_ROOT,results);
        assertArrayPermutation(TEST_FILE_LIST,walker.getFiles());
        assertArrayPermutation(TEST_DIR_LIST,walker.getDirectories());
        assertArrayPermutation
            (ArrayUtils.addAll(TEST_FILE_LIST,TEST_DIR_LIST),
             results.toArray(new String[0]));
        assertEquals(3,walker.getMaxDepth());
    }

    @Test
    public void unixWalk()
    {
        assumeTrue(OperatingSystem.LOCAL.isWin32());
    }
}
