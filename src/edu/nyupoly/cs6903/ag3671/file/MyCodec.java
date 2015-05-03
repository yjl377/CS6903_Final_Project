package edu.nyupoly.cs6903.ag3671.file;

import java.io.IOException;
import java.util.Optional;

import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;

import com.ctc.wstx.dtd.OptionalModel;

public class MyCodec <T extends BaseFileStructure> {
	
	private final Codec<T> codec;
	private final Class<T> clazz;
	
	public MyCodec(Class<T> clazz) {
		this.clazz = clazz;
		codec = Codecs.create(clazz);
	}
	
	public Optional<byte[]> encode(T obj) {
		
		try {
			return Optional.of(Codecs.encode(obj, codec));
			
		} catch (IOException e) {
			
		}
		
				
		return Optional.empty();
	}
	
	public Optional<T> decode(byte[] arr) {
		
		try {
			return Optional.of(Codecs.decode(codec, arr));
		} catch (DecodingException e) {
			
		}
		
		return Optional.empty();
	}
	
}
