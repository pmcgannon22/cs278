import sys
import os
import subprocess
from DropboxTest import DropboxTest
import unittest
from time import sleep
import fileutils

def mkdir_p(path):
    try:
        os.makedirs(path)
    except OSError as exc: # Python >2.5
        if os.path.isdir(path):
            pass
        else: raise

if __name__ == '__main__':
    client_dir = 'JavaTest2'
    server_dir = 'JavaTest'

    mkdir_p(client_dir)
    mkdir_p(server_dir)

    p = subprocess.Popen('java -jar dropbox.jar ./%s/' % server_dir, shell=True, 
                         stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    print 'Starting Dropbox server...'
    sleep(10)
    p2 = subprocess.Popen('java -jar dropbox.jar ./%s/ 127.0.0.1' % client_dir, shell=True,
                          stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    print 'Starting Dropbox client...'
    sleep(25)
    print 'Starting Dropbox tests...'
    unittest.main(verbosity=2, exit=False)

    fileutils.empty_directory(client_dir)
    fileutils.empty_directory(server_dir)


    p.kill()
    p2.kill()
