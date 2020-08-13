// 
// Decompiled by Procyon v0.5.36
// 

package com.example.nibsskeyexchangtrannotification.utils.Nibss.models.transaction;

public class _0200Request extends ISO8583TransactionRequest
{
    @Override
    public boolean hashMessage() {
        return true;
    }
    
    @Override
    public int getMessageType() {
        return 512;
    }
}
