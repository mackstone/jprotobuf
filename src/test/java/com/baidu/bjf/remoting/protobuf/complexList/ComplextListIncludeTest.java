/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Baidu company (the "License");
 * you may not use this file except in compliance with the License.
 *
 */
package com.baidu.bjf.remoting.protobuf.complexList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.complexList.AddressBookProtos.AddressBook;
import com.baidu.bjf.remoting.protobuf.complexList.AddressBookProtos.Person;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 *
 * @author xiemalin
 *
 */
public class ComplextListIncludeTest {

 
    /**
     * test encode and decode with JProtobuf and Protobuf java
     */
    @Test
    public void testEncodeDecode() {

        Person p = Person.newBuilder().
        setName("xiemalin").setId(100).build();
        
        AddressBook book = AddressBook.newBuilder().addPerson(p).build();
        
        byte[] bb = book.toByteArray();
        System.out.println(Arrays.toString(bb));
        
        Codec<AddressBookProtosPOJO> codec = ProtobufProxy.create(AddressBookProtosPOJO.class);
        
        AddressBookProtosPOJO pojo = new AddressBookProtosPOJO();
        
        PersonPOJO person = new PersonPOJO();
        person.name = "xiemalin";
        person.id = 100;
/*        person.boolF = true;
        person.bytesF = new byte[]{1,2};
        person.email = "xiemalin@baidu.com";*/
        
        
        List<PersonPOJO> list = new ArrayList<PersonPOJO>();
        list.add(person);
        list.add(person);
        pojo.list = list;
        
        bb =  null;
        try {
            bb = codec.encode(pojo);
            System.out.println(Arrays.toString(bb));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        try {
            AddressBook parseFrom = AddressBook.parseFrom(bb);
            Assert.assertEquals(2, parseFrom.getPersonCount());
            Assert.assertEquals("xiemalin", parseFrom.getPerson(0).getName());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        
        try {
            AddressBookProtosPOJO decode = codec.decode(bb);
            Assert.assertEquals(2, decode.list.size());
            Assert.assertEquals("xiemalin", decode.list.get(0).name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}