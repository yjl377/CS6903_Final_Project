package edu.nyupoly.cs6903.ag3671;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import org.codehaus.preon.NullResolver;
import org.codehaus.preon.buffer.ByteOrder;
import org.codehaus.preon.channel.BitChannel;
import org.codehaus.preon.channel.OutputStreamBitChannel;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.nyupoly.cs6903.ag3671.file.MyCodec;
import edu.nyupoly.cs6903.ag3671.file.EncryptedFile;

public class TestPreon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] buff = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
		EncryptedFile key = new EncryptedFile();
		key.setKeyLen(buff.length);
		key.setKey(buff);
		Codec<Rectangle> codec = Codecs.create(Rectangle.class);
		try {
			Rectangle rect = Codecs.decode(codec, buff);
			
			
			System.out.println(rect.toString());
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			OutputStreamBitChannel bits = new OutputStreamBitChannel(stream);
			codec.encode(rect, bits, new NullResolver());
			stream.close();
			
			MyCodec<EncryptedFile> mycodec = new MyCodec<EncryptedFile>(EncryptedFile.class);
			System.out.println(Arrays.toString(mycodec.encode(key).get()));
		} catch (DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

