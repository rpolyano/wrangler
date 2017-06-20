grammar Test;

METRIC: 'metric';
ANY: .;

text: ANY* EOF;

args
  : (ANY*) METRIC EOF
  ;