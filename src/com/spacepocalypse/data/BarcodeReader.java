package com.spacepocalypse.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.UPCAReader;


public class BarcodeReader {
	
	public static String decodeDataFromImage(String fileName) {
		Reader reader = new UPCAReader();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("/home/dylan/Pictures/barcode.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (img == null) {
			return null;
		}
		
		LuminanceSource source = new BufferedImageLuminanceSource(img);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
		Result result = null;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		
		try {
			
			result = reader.decode(bitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
		
		if (result == null) {
			return null;
		}
		
		return result.getText();
	}
	
	public static void main(String[] args) {
		Reader reader = new UPCAReader();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("/home/dylan/Pictures/barcode.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (img == null) {
			System.out.println("ERROR LOADING IMAGE.");
			System.exit(1);
		}
		
		LuminanceSource source = new BufferedImageLuminanceSource(img);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
		Result result = null;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		
		try {
			
			result = reader.decode(bitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
		
		if (result == null) {
			System.out.println("ERROR DECODING.");
			System.exit(1);
		}
		
		System.out.println("RESULT = [" + result.getText() + "]");
		
	}
}
