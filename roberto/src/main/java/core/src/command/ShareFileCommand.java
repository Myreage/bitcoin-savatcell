package command;

import beans.FileStored;

/**
 * ShareFileCommand This command contains the fileToShare which itself 
 * contains the file and the FILEDESC
 * *  */
public class ShareFileCommand extends Command {

	private FileStored fileToShare;
	
	// shareFile

	public ShareFileCommand(int numCommand, FileStored file) {
		super(numCommand);
		this.fileToShare = file;
	}

	public FileStored getFileToShare() {
		return fileToShare;
	}

	public void setFileToShare(FileStored file) {
		this.fileToShare = file;
	}

}
