import os
d = os.path.join(os.path.dirname(os.path.abspath(__file__)), '项目管理')
os.makedirs(d, exist_ok=True)
print(d)
