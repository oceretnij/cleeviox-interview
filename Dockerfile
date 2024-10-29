FROM ethereum/client-go:v1.10.1

ARG ACCOUNT_PASSWORD

COPY genesis.json .

RUN geth init ./genesis.json \
    && rm -f ~/.ethereum/geth/nodekey \
    && echo changeyourpassword > ./password.txt \
    && geth account new --password ./password.txt \
    && rm -f ./password.txt

#RUN sleep 15
#
#RUN ENODE_OUTPUT=$(geth attach http://127.0.0.1:8545 --exec 'admin.nodeInfo.enode') \
#    && echo "ENODE_OUTPUT: ${ENODE_OUTPUT}" \
#    && ENODE_ADDRESS=$(echo ${ENODE_OUTPUT} | tr -d '"') \
#    && echo "ENODE_ADDRESS: ${ENODE_ADDRESS}" \
#    && geth --bootnodes="enode://${ENODE_ADDRESS}@127.0.0.1:30303" --mine --networkid=3456 \
#    && geth --bootnodes="enode://${ENODE_ADDRESS}@127.0.0.1:30303" --mine --networkid=3456 \
#    && geth --bootnodes="enode://${ENODE_ADDRESS}@127.0.0.1:30303" --mine --networkid=3456 \
#    && geth --bootnodes="enode://${ENODE_ADDRESS}@127.0.0.1:30303" --mine --networkid=3456 \
#    && geth --bootnodes="enode://${ENODE_ADDRESS}@127.0.0.1:30303" --mine --networkid=3456

ENTRYPOINT ["geth"]
