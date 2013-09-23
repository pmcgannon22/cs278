import unittest
from time import sleep
import filecmp
import fileutils
import os


class DropboxTest(unittest.TestCase):
    def setUp(self):
        self.clientdir = 'JavaTest2'
        self.serverdir = 'JavaTest'

    def file_create(self, first, second):
        open(first,'a').close()
        self.assertTrue(fileutils.file_exists(first), msg="Initial file creation failed.")

        sleep(10)

        try:
            with open(second):
                self.assertTrue(filecmp.cmp(first,second), msg="Files are not the same.")
        except:
            self.fail(msg="File was not created in server directory.")

    def test_create_empty_file_client(self):
        name = 'create_empty_file_client'
        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)
        self.file_create(f_client, f_server)

    def test_create_empty_file_server(self):
        name = 'create_empty_file_server'
        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)
        self.file_create(f_server, f_client)

    def test_modify_file_client(self):
        name = 'modify_file_client'
        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)

        with open(f_client,'w') as f:
            f.write(name)
        self.assertTrue(fileutils.file_exists(f_client), msg="Initial file creation failed.")
        
        sleep(15)

        try:
            with open(f_server):
                self.assertTrue(filecmp.cmp(f_client,f_server), msg="Files are not the same.")
        except:
            self.fail(msg="File was not created in server directory.")


    def test_modify_file_server(self):
        name = 'modify_file_server'
        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)

        with open(f_server,'w') as f:
            f.write(name)
        self.assertTrue(fileutils.file_exists(f_server), msg="Initial file creation failed.")
        
        sleep(10)

        try:
            with open(f_client):
                self.assertTrue(filecmp.cmp(f_client,f_server), msg="Files are not the same.")
        except:
            self.fail(msg="File was not created in server directory.")

    def test_delete_file_client(self):
        name = 'delete_file_client'

        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)
        self.file_create(f_client, f_server)

        fileutils.remove_file(f_client)
        self.assertFalse(fileutils.file_exists(f_client))

        sleep(10)

        self.assertFalse(fileutils.file_exists(f_server))

    def test_delete_file_server(self):
        name = 'delete_file_server'

        f_client = '%s/%s.txt' % (self.clientdir, name)
        f_server = '%s/%s.txt' % (self.serverdir, name)
        self.file_create(f_server, f_client)

        fileutils.remove_file(f_server)
        self.assertFalse(fileutils.file_exists(f_server))

        sleep(10)

        self.assertFalse(fileutils.file_exists(f_client))

    def test_file_within_folder_client(self):
        name = 'file_within_folder_client.txt'
        client_directory = '%s/test-dir' % self.clientdir
        server_directory = '%s/test-dir' % self.serverdir

        fileutils.create_file_within_dir(client_directory, name) 
        self.assertTrue(fileutils.file_exists(os.path.join(client_directory, name)))
        sleep(15)
        self.assertTrue(fileutils.file_exists(os.path.join(server_directory, name)))
        
