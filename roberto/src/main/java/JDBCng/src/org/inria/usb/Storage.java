/**
 * 
 */
package org.inria.usb;

import java.io.IOException;

import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

import org.inria.database.DBEngine;

/**
 * Implementation of DBEngine interface
 * 
 * @author Quentin Lefebvre
 */
public class Storage extends DBEngine
{
	private final static int TIMEOUT_READ  = 120000;	// 45 sec (wiping NOR typically should not take more than 12 sec ; you need to add time to erase NAND recorder structures)
	private final static int TIMEOUT_WRITE = 3000;	// 3 sec

	protected VCPDriver mVCPDriver;
	private static Storage mInstance = null;

	protected Storage()
	{
		mVCPDriver = null;
	}

	public static Storage instance()
	{
		if ( mInstance == null )
		{
			mInstance = new Storage();
			DBEngine.mInstance = mInstance;
		}
		return mInstance;
	}

	/**
	 * Attach VCPDriver to the given port
	 * 
	 * @param portName	the port to use (e.g. "COM1", "LPT1", ...)
	 * @return device serial number string
	 */
	public boolean attach( String portName ) throws Exception
	{
		System.out.println( "Attach driver to communication device" );
		// attach driver to communication device...
		// check if device is found and operational...
		mVCPDriver = VCPDriver.openDriver( portName );
		if ( isAttached() )
			return true;
		else {
			System.out.println( "Init failed" );
			return false;
		}
	}

	/**
	 * Detach driver from previously attached UsbDevice
	 */
	public void detach() throws Exception
	{
		if ( isAttached() )
		{
			System.out.println( "Detach driver from communication device" );
			mVCPDriver.close();
			mVCPDriver = null;
		}
	}

	/**
	 * Check if driver is attached to the given UsbDevice
	 * 
	 * @param usbDevice		the {@link UsbDevice} to check
	 * @return true if driver is attached to the given UsbDevice
	 */
	public boolean isAttached( final String portName )
	{
		if ( !isAttached() || portName == null )
			return false;
		return true;
	}

	@Override
	public boolean isAttached()
	{
		return (mVCPDriver != null);
	}

	@Override
	protected void read( byte[] buf ) throws IOException
	{
		if ( !isAttached() )
			throw new IOException( "Error reading detached device" );
		try
		{
			if ( mVCPDriver.read( buf, TIMEOUT_READ ) < buf.length )
				throw new IOException( "Error reading USB device" );
		} catch (SerialPortException e)
		{
			throw new IOException( "SerialPortException while reading USB device" );
		} catch (SerialPortTimeoutException e) {
			throw new IOException( "Timeout ellapsed while reading USB device" );
		}
	}

	@Override
//	protected void write( byte[] buf ) throws IOException
	public void write( byte[] buf ) throws IOException
	{
		if ( !isAttached() )
			throw new IOException( "Error writing detached device" );
		try
		{
			if ( mVCPDriver.write( buf, TIMEOUT_WRITE ) < buf.length )
				throw new IOException( "Error writing USB device" );
		} catch (SerialPortException e)
		{
			throw new IOException( "SerialPortException while writing USB device" );
		}
	}
}
