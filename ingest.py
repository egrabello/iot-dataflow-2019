import os
import time
from shutil import copyfile

source_path = "/Users/egberto/nifi_data_source/"
destin_path = "/Users/egberto/nifi_input/"

file_list = []

for root, dirs, files in os.walk(source_path):  
    for filename in files:
        #print(filename)
        file_list.append(filename)

file_list.sort()

file_list.remove(".DS_Store")
for item in file_list:
    copyfile(source_path + item, destin_path + item)
    time.sleep(1)
