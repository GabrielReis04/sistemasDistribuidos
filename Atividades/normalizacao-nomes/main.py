import os
import sys

BASE_DIR = os.path.dirname(__file__)
for pasta in ("controller", "model", "view"):
    sys.path.append(os.path.join(BASE_DIR, pasta))

from controller import iniciar

iniciar()
