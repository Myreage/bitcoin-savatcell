package org.inria.usb;

import java.io.IOException;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

/**
 * @author Quentin Lefebvre
 */
public class VCPDriver
{
	public static final short DEFAULT_READ_BUFFER_SIZE = (short) 64;	// must be equal to packet size
	public static final short DEFAULT_WRITE_BUFFER_SIZE = (short) 1024;
	public static final int DEFAULT_BAUDRATE = SerialPort.BAUDRATE_115200;
	public static final byte DEFAULT_NUM_STOPBITS = 1;
	public static final byte DEFAULT_PARITY = 0;
	public static final byte DEFAULT_NUM_DATABITS = 8;

	/** Internal read buffer guarded by {@link #mLockRead} */
	protected byte[] mBufferRead;
	protected final Object mLockRead = new Object();
	protected short nbRead, nbOffset;
	protected String mPortName ;
	protected SerialPort mSerialPort ;
	
	/** Internal write buffer guarded by {@link #mLockWrite} */
	protected byte[] mBufferWrite;
	protected final Object mLockWrite = new Object();

	// =============== CONSTRUCTOR =================
	protected VCPDriver( String portName )
	{
		mPortName = portName;
		mSerialPort = null;
		
		mBufferRead = new byte[ DEFAULT_READ_BUFFER_SIZE ];
		nbRead = nbOffset = 0;
		mBufferWrite = new byte[ DEFAULT_WRITE_BUFFER_SIZE ];
	}

	// =============== PRIVATE METHODS =================
	/**
	 * Opens and initializes Virtual COM port USB device. Upon success,
	 * caller must ensure that {@link #close()} is eventually called.
	 * @throws SerialPortException 
	 */
	private void open() throws SerialPortException 
	{
        mSerialPort = new SerialPort(mPortName);
        mSerialPort.openPort();
		System.out.println( "Communication device opened." );
        
        //Set params
        mSerialPort.setParams(DEFAULT_BAUDRATE, DEFAULT_NUM_DATABITS, DEFAULT_NUM_STOPBITS, DEFAULT_PARITY);
        mSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		System.out.println( "Communication device set up." );
	}


	// =============== PUBLIC METHODS =================
	/**
	 * Builds and returns a new {@link VCPDriver} from the given {@link UsbDevice}
	 * or returns {@code null} if error occurred opening this device.
	 * 
	 * @param portName
	 *            the COM port to use
	 * @return a new {@link VCPDriver}, or {@code null} if no devices could be acquired
	 */
	public static VCPDriver openDriver( String portName )
	{
		VCPDriver drv = new VCPDriver( portName );
		try
		{
			drv.open();
		}
		catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return drv;
	}

	/**
	 * Closes Virtual COM port USB device
	 * @throws SerialPortException 
	 */
	public void close() throws SerialPortException
	{
		if ( mSerialPort != null )
		{
			mSerialPort.closePort();
			mSerialPort = null;
		}
	}

	/**
	 * Reads as many bytes as possible into destination buffer
	 * 
	 * @param dest
	 *            destination byte buffer
	 * @param timeoutMillis
	 *            timeout for reading
	 * @return actual number of bytes read
	 * @throws SerialPortTimeoutException 
	 * @throws SerialPortException 
	 */
	public int read( byte[] dest, int timeoutMillis ) throws SerialPortException, SerialPortTimeoutException
	{
		int nbLeft;

		synchronized ( mLockRead )
		{
			nbLeft = dest.length;
	        do
			{
	        	if ( nbRead > 0 )
				{	// nbRead bytes are in read buffer...
					int nb = Math.min( nbLeft, nbRead );
					System.arraycopy( mBufferRead, nbOffset, dest, dest.length - nbLeft, nb );
					nbOffset += nb;
					nbRead -= nb;
					nbLeft -= nb;
				}
				else
				{	// read buffer is empty, read more packets...
					nbOffset = 0;
					int n = (nbLeft <= DEFAULT_READ_BUFFER_SIZE) ? nbLeft : DEFAULT_READ_BUFFER_SIZE;
					byte[] buffer = mSerialPort.readBytes(n, timeoutMillis);
					System.arraycopy( buffer, 0, mBufferRead, 0, n );
					nbRead = (short) n;
				}
			} while ( nbLeft > 0 );
		}
		return dest.length - nbLeft;
	}

	/**
	 * Writes as many bytes as possible from source buffer
	 * 
	 * @param src
	 *            source byte buffer
	 * @param timeoutMillis
	 *            timeout for writing
	 * @return actual number of bytes written
	 * @throws SerialPortException 
	 */
	public int write( byte[] src, int timeoutMillis ) throws SerialPortException
	{
		synchronized ( mLockWrite )
		{
			mSerialPort.writeBytes( src );
		}
		return src.length;
	}

	public String getDeviceSerial()
	{
		return "getDeviceSerial() not implemented !";
	}

	/**
	 * Sets baud rate of Virtual COM port device.
	 * 
	 * @param baudRate
	 *            the desired baud rate, in bits per second
	 * @return the actual rate set
	 * @throws SerialPortException 
	 */
	public int setBaudRate( int baudRate ) throws SerialPortException
	{
        mSerialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		mSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
//		mSerialPort.setEndOfInputChar(SET_LINE_CODING);
//		mSerialPort.setRTS(false);
//		mSerialPort.setDTR(false);
//		mSerialPort.enableReceiveThreshold(1);
//		mSerialPort.enableReceiveTimeout(3000);
		return baudRate;
	}

	// =============== PRIVATE METHODS =================

	// =============== CONFIGURATION METHODS =================

	/**
	 * Returns the currently-bound serial port device.
	 * 
	 * @return Serial port device
	 */
	public final SerialPort getDevice()
	{
		return mSerialPort;
	}

	/**
	 * Sets the size of the internal buffer used to exchange data with the USB
	 * stack for read operations. Most users should not need to change this.
	 * 
	 * @param bufferSize
	 *            the size in bytes
	 */
	public final void setReadBufferSize( int bufferSize )
	{
		synchronized ( mLockRead )
		{
			if ( bufferSize != mBufferRead.length )
				mBufferRead = new byte[ bufferSize ];
		}
	}

	/**
	 * Sets the size of the internal buffer used to exchange data with the USB
	 * stack for write operations. Most users should not need to change this.
	 * 
	 * @param bufferSize
	 *            the size in bytes
	 */
	public final void setWriteBufferSize( int bufferSize )
	{
		synchronized ( mLockWrite )
		{
			if ( bufferSize != mBufferWrite.length )
				mBufferWrite = new byte[ bufferSize ];
		}
	}

	// =============== PUBLIC DUMMY METHODS =================

	/**
	 * Gets the CD (Carrier Detect) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 */
	public boolean getCD() throws IOException
	{
		return false;
	}

	/**
	 * Gets the CTS (Clear To Send) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 * @throws SerialPortException 
	 */
	public boolean getCTS() throws SerialPortException
	{
		return (mSerialPort.getLinesStatus()[0] == 1);
	}

	/**
	 * Gets the DSR (Data Set Ready) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 * @throws SerialPortException 
	 */
	public boolean getDSR() throws SerialPortException
	{
		return (mSerialPort.getLinesStatus()[1] == 1);
	}

	/**
	 * Gets the DTR (Data Terminal Ready) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 */
	public boolean getDTR() throws IOException
	{
		return false;
	}

	/**
	 * Sets the DTR (Data Terminal Ready) bit on the underlying UART, if
	 * supported.
	 * 
	 * @param value
	 *            the value to set
	 */
	public boolean setDTR( boolean value ) throws IOException
	{
		// TODO : implement this
		return false;
	}

	/**
	 * Gets the RI (Ring Indicator) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 * @throws SerialPortException 
	 */
	public boolean getRI() throws SerialPortException  
	{
		return mSerialPort.isRING();
	}

	/**
	 * Gets the RTS (Request To Send) bit from the underlying UART.
	 * 
	 * @return the current state, or {@code false} if not supported.
	 */
	public boolean getRTS() throws IOException
	{
		// TODO : implement this
		return false;
	}

	/**
	 * Sets the RTS (Request To Send) bit on the underlying UART, if supported.
	 * 
	 * @param value
	 *            the value to set
	 * @throws SerialPortException 
	 */
	public boolean setRTS( boolean value ) throws SerialPortException
	{
		return mSerialPort.setRTS(value);
	}
}

