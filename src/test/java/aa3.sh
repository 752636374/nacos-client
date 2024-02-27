#!/bin/bash

file="/Users/yaoheng5/Desktop/项目文档/产品文档/质检/口头语不扣-20231226144609/audio/output.wav"
block_size=8000  # 8k
file_size=$(wc -c < "$file" | awk '{print $1}')
total_blocks=$((file_size / block_size))
seq=1
echo 文件大小$total_blocks
for ((blocks=0; blocks<total_blocks; blocks++)); do
  valsum=$((total_blocks - 1))
  if [ $blocks -eq $valsum ]; then
      seq=-$seq
  fi
  chunk=$(dd if="$file" bs=1 skip=$((block_size * blocks)) count=$block_size status=none )

  result=$(curl -X POST \
    -H 'Content-Type: application/json' \
    -H 'Domain: general_8k' \
    -H 'Application-Id: 9d54337c-6178-4e01-a2ce-692337efb48a-guowang' \
    -H 'Request-Id: 56a847e6-84c0-4c01-bf4b-d566f2d2dd35' \
    -H "Sequence-Id: $seq" \
    -H 'Asr-Protocol: 3' \
    -H 'Net-State: 2' \
    -H 'Applicator: 1' \
    -H 'Property: {"autoend": false, "encode": {"channel": 1, "format": "wav", "sample_rate": 8000, "post_process": -1, "partial_result": -1, "punc_partial_process": -1, "punc_end_process": -1}, "platform": "Linux&Centos&7.3", "version": "0.0.0.1", "longspeech": true}' \
    --data-raw "{\"audio\": \"$chunk\"}" \
    'https://asrapi-base.jd.com/asr')

  echo "Response for Sequence-Id $seq: $result"
  ((seq++))
done
