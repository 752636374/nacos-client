#!/bin/bash

seq=1
file="/Users/yaoheng5/Desktop/项目文档/产品文档/质检/口头语不扣-20231226144609/audio/output.wav"
file_size=$(wc -c < "$file")
block_size=18192
total_blocks=$((file_size / block_size))
blocks=1
while [ $blocks -le $total_blocks ]; do
  read -n $block_size -r chunk < "$file"

  # 发送请求到远程地址
  # 假设使用 curl 命令发送请求
  result=$(  curl -XPOST \
-H 'Content-Type:application/octet-stream' \
-H 'Domain:general_8k' \
-H 'Application-Id:9d54337c-6178-4e01-a2ce-692337efb48a-guowang' \
-H 'Request-Id:56a847e6-84c0-4c01-bf4b-d566f2d2dd36' \
-H 'Sequence-Id: $seq' \
-H 'Asr-Protocol:3' \
-H 'Net-State:2' \
-H 'Applicator:1' \
-H 'Property:{"autoend":false, "encode":{"channel":1,"format":"wav", "sample_rate":8000,"post_process":-1,"partial_result":-1,"punc_partial_process":-1, "punc_end_process":-1}, "platform":"Linux&Centos&7.3", "version":"0.0.0.1", "longspeech":true}' \
--data-binary '$chunk' \
'https://asrapi-base.jd.com/asr')
  echo "Response for Sequence-Id $seq: $result"

  ((seq++))
  ((blocks++))
done

# 处理最后一个块
seq=-$seq
tail -c +$((total_blocks * block_size + 1)) "$file" | read -n $((file_size - total_blocks * block_size)) -r chunk
# 发送请求到远程地址
# 假设使用 curl 命令发送请求
result=$(curl -XPOST \
-H 'Content-Type:application/octet-stream' \
-H 'Domain:general_8k' \
-H 'Application-Id:9d54337c-6178-4e01-a2ce-692337efb48a-guowang' \
-H 'Request-Id:56a847e6-84c0-4c01-bf4b-d566f2d2dd35' \
-H 'Sequence-Id: -$seq' \
-H 'Asr-Protocol:3' \
-H 'Net-State:2' \
-H 'Applicator:1' \
-H 'Property:{"autoend":false, "encode":{"channel":1,"format":"wav", "sample_rate":8000,"post_process":-1,"partial_result":-1,"punc_partial_process":-1, "punc_end_process":-1}, "platform":"Linux&Centos&7.3", "version":"0.0.0.1", "longspeech":true}' \
--data-binary '$chunk' \
'https://asrapi-base.jd.com/asr')
echo "Response for Sequence-Id $seq: $result"
