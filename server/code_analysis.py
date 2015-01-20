import os
import ast
import time
import json
import collections
import subprocess

FILE_SCRIPT = 'script.py'
FILE_SCRIPTS_RESULT = "result_of_script.txt"
WRITE_FILE = 'w'
READ_FILE = 'r'

TAG_RESULT = 'RESULT'
TAG_RUNTIME = 'RUNTIME'
TAG_SCRIPT_SIZE = 'SCRIPT_SIZE'
TAG_LINES = 'LINES'
TAG_STRINGS = 'STRINGS'
TAG_NUMBERS = 'NUMBERS'
TAG_CLASSES = 'CLASSES'
TAG_FUNCTIONS = 'FUNCTIONS'
TAG_BINARY_OPERATIONS = 'BINARY_OP'
TAG_WHILE_STATEMENTS = 'WHILE_ST'
TAG_FOR_STATEMENTS = 'FOR_ST'
TAG_IF_STATEMENTS = 'IF_ST'
TAG_SCRIPT = 'SCRIPT'

# Saving to file
def save_to_file(inp_script):
    json_object = json.loads(inp_script)
    script_string = json_object[TAG_SCRIPT]
    file_script = open(FILE_SCRIPT, WRITE_FILE)
    file_script.write(script_string)
    file_script.close()

# Getting result after executing code 
def get_result_from_script(file_name):
    result = subprocess.check_output(["python", file_name])
    return result;

# Getting runtime of code
def get_runtime_of_script(file_name):
    time_start = time.time()
    execfile(file_name)
    time_end = time.time()
    time_result = time_end - time_start
    return time_result

# Parsing code to get syntax statistic
def get_syntax_stats(file_name):    
    syntax_tree = ast.parse(open(file_name).read(), file_name)
    all_stats = collections.defaultdict(int)
    for node in ast.walk(syntax_tree):
        all_stats[type(node)] += 1    
    return all_stats

# Getting codes amount of lines 
def get_count_lines_of_code(file_name):
    num_lines = sum(1 for line in open(file_name))
    return num_lines

# Getting scripts size 
def get_size_of_script(file_name):
    script_size = os.stat(file_name)
    return script_size.st_size

# Getting all code analysis of code in json
def get_json_all_analyze_of_code(file_name):
    syntax_stats_of_code = get_syntax_stats(file_name)
    data = {}    
    data[TAG_RESULT] =  get_result_from_script(file_name)
    data[TAG_RUNTIME] = get_runtime_of_script(file_name)
    data[TAG_SCRIPT_SIZE] = get_size_of_script(file_name)
    data[TAG_LINES] = get_count_lines_of_code(file_name)
    data[TAG_NUMBERS] = syntax_stats_of_code[ast.Num]
    data[TAG_STRINGS] = syntax_stats_of_code[ast.Str]
    data[TAG_NUMBERS] = syntax_stats_of_code[ast.Num]
    data[TAG_CLASSES] = syntax_stats_of_code[ast.ClassDef]
    data[TAG_FUNCTIONS] = syntax_stats_of_code[ast.FunctionDef]
    data[TAG_BINARY_OPERATIONS] = syntax_stats_of_code[ast.BinOp]
    data[TAG_WHILE_STATEMENTS] = syntax_stats_of_code[ast.While]
    data[TAG_FOR_STATEMENTS] = syntax_stats_of_code[ast.For]
    data[TAG_IF_STATEMENTS] = syntax_stats_of_code[ast.If]

    json_data = json.dumps(data)
    return json_data