#!/bin/bash

# Initialize Sequence-Id to 1
seq=1

# Read the wav file, 8k at a time, and call the remote API
while IFS= read -r -n 8192 chunk
  do
    if [ -z "$chunk" ]; then
      break
    fi

    result=$(curl -s -X POST \
    -H 'Content-Type: application/octet-stream' \
    -H 'Domain: general_8k' \
    -H 'Application-Id: 9d54337c-6178-4e01-a2ce-692337efb48a-guowang' \
    -H 'Request-Id: 56a847e6-84c0-4c01-bf4b-d566f2d2dd35' \
    -H "Sequence-Id: $seq" \
    -H 'Asr-Protocol: 3' \
    -H 'Net-State: 2' \
    -H 'Applicator: 1' \
    -H 'Property: {"autoend": false, "encode": {"channel": 1, "format": "wav", "sample_rate": 8000, "post_process": -1, "partial_result": -1, "punc_partial_process": -1, "punc_end_process": -1}, "platform": "Linux&Centos&7.3", "version": "0.0.0.1", "longspeech": true}' \
    --data-binary "$chunk" 'https://asrapi-base.jd.com/asr')
    echo "Response for Sequence-Id $seq: $result"

    ((seq++))
  done < /Users/yaoheng5/Desktop/项目文档/产品文档/质检/口头语不扣-20231226144609/audio/output.wav