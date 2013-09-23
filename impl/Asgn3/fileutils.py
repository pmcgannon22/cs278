import shutil
import os

def file_exists(filename):
    try:
        with open(filename):
            return True
    except:
        return False

def create_file_within_dir(directory, filename):
    try:
        os.makedirs(directory)
    except OSError as exc: # Python >2.5
        if os.path.isdir(directory):
            pass
    p = os.path.join(directory, filename)
    open(p, 'a').close()

def empty_directory(directory):
    shutil.rmtree(directory)

def remove_file(filename):
    os.remove(filename)
