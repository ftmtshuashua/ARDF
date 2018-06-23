/*
    2    * Copyright (c) 1995, 2005, Oracle and/or its affiliates. All rights reserved.
    3    * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
    4    *
    5    * This code is free software; you can redistribute it and/or modify it
    6    * under the terms of the GNU General Public License version 2 only, as
    7    * published by the Free Software Foundation.  Oracle designates this
    8    * particular file as subject to the "Classpath" exception as provided
    9    * by Oracle in the LICENSE file that accompanied this code.
   10    *
   11    * This code is distributed in the hope that it will be useful, but WITHOUT
   12    * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
   13    * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
   14    * version 2 for more details (a copy is included in the LICENSE file that
   15    * accompanied this code).
   16    *
   17    * You should have received a copy of the GNU General Public License version
   18    * 2 along with this work; if not, write to the Free Software Foundation,
   19    * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
   20    *
   21    * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
   22    * or visit www.oracle.com if you need additional information or have any
   23    * questions.
   24    */

package sun.misc;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder
{
	protected PrintStream pStream;

	abstract protected int bytesPerAtom();

	abstract protected int bytesPerLine();

	protected void encodeBufferPrefix(OutputStream aStream) throws IOException
	{
		pStream = new PrintStream(aStream);
	}

	protected void encodeBufferSuffix(OutputStream aStream) throws IOException
	{
	}

	protected void encodeLinePrefix(OutputStream aStream, int aLength)
			throws IOException
	{
	}

	protected void encodeLineSuffix(OutputStream aStream) throws IOException
	{
		pStream.println();
	}

	abstract protected void encodeAtom(OutputStream aStream, byte someBytes[],
			int anOffset, int aLength) throws IOException;

	protected int readFully(InputStream in, byte buffer[])
			throws IOException
	{
		for (int i = 0; i < buffer.length; i++)
		{
			int q = in.read();
			if (q == -1)
				return i;
			buffer[i] = (byte) q;
		}
		return buffer.length;
	}

	public void encode(InputStream inStream, OutputStream outStream)
			throws IOException
	{
		int j;
		int numBytes;
		byte tmpbuffer[] = new byte[bytesPerLine()];

		encodeBufferPrefix(outStream);

		while (true)
		{
			numBytes = readFully(inStream, tmpbuffer);
			if (numBytes == 0)
			{
				break;
			}
			encodeLinePrefix(outStream, numBytes);
			for (j = 0; j < numBytes; j += bytesPerAtom())
			{

				if ((j + bytesPerAtom()) <= numBytes)
				{
					encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
				} else
				{
					encodeAtom(outStream, tmpbuffer, j, (numBytes) - j);
				}
			}
			if (numBytes < bytesPerLine())
			{
				break;
			} else
			{
				encodeLineSuffix(outStream);
			}
		}
		encodeBufferSuffix(outStream);
	}

	public void encode(byte aBuffer[], OutputStream aStream) throws IOException
	{
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		encode(inStream, aStream);
	}

	public String encode(byte aBuffer[])
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		String retVal = null;
		try
		{
			encode(inStream, outStream);
			// explicit ascii->unicode conversion
			retVal = outStream.toString("8859_1");
		} catch (Exception IOException)
		{
			// This should never happen.
			throw new Error("CharacterEncoder.encode internal error");
		}
		return (retVal);
	}

	private byte[] getBytes(ByteBuffer bb)
	{
		/*
		 * This should never return a BufferOverflowException, as we're 213 *
		 * careful to allocate just the right amount. 214
		 */
		byte[] buf = null;

		/*
		 * 218 * If it has a usable backing byte buffer, use it. Use only 219 *
		 * if the array exactly represents the current ByteBuffer. 220
		 */
		if (bb.hasArray())
		{
			byte[] tmp = bb.array();
			if ((tmp.length == bb.capacity()) && (tmp.length == bb.remaining()))
			{
				buf = tmp;
				bb.position(bb.limit());
			}
		}

		if (buf == null)
		{
			/*
			 * 232 * This class doesn't have a concept of encode(buf, len, off),
			 * 233 * so if we have a partial buffer, we must reallocate 234 *
			 * space. 235
			 */
			buf = new byte[bb.remaining()];

			/*
			 * 239 * position() automatically updated 240
			 */
			bb.get(buf);
		}

		return buf;
	}

	public void encode(ByteBuffer aBuffer, OutputStream aStream)
			throws IOException
	{
		byte[] buf = getBytes(aBuffer);
		encode(buf, aStream);
	}

	public String encode(ByteBuffer aBuffer)
	{
		byte[] buf = getBytes(aBuffer);
		return encode(buf);
	}

	public void encodeBuffer(InputStream inStream, OutputStream outStream)
			throws IOException
	{
		int j;
		int numBytes;
		byte tmpbuffer[] = new byte[bytesPerLine()];

		encodeBufferPrefix(outStream);

		while (true)
		{
			numBytes = readFully(inStream, tmpbuffer);
			if (numBytes == 0)
			{
				break;
			}
			encodeLinePrefix(outStream, numBytes);
			for (j = 0; j < numBytes; j += bytesPerAtom())
			{
				if ((j + bytesPerAtom()) <= numBytes)
				{
					encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
				} else
				{
					encodeAtom(outStream, tmpbuffer, j, (numBytes) - j);
				}
			}
			encodeLineSuffix(outStream);
			if (numBytes < bytesPerLine())
			{
				break;
			}
		}
		encodeBufferSuffix(outStream);
	}

	public void encodeBuffer(byte aBuffer[], OutputStream aStream)
			throws IOException
	{
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		encodeBuffer(inStream, aStream);
	}

	public String encodeBuffer(byte aBuffer[])
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
		try
		{
			encodeBuffer(inStream, outStream);
		} catch (Exception IOException)
		{
			// This should never happen.
			throw new Error("CharacterEncoder.encodeBuffer internal error");
		}
		return (outStream.toString());
	}

	public void encodeBuffer(ByteBuffer aBuffer, OutputStream aStream)
			throws IOException
	{
		byte[] buf = getBytes(aBuffer);
		encodeBuffer(buf, aStream);
	}

	public String encodeBuffer(ByteBuffer aBuffer)
	{
		byte[] buf = getBytes(aBuffer);
		return encodeBuffer(buf);
	}

}