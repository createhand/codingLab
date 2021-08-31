/**
 * Copyright (c) 2016 KBCARD, Inc.
 * All right reserved..
 */
package codingTest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 파일 업로드와 관련 된 클래스
 * </p>
 * @author 2016. 08. 01., TA
 */
public class FileUploadUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	private static final int IMG_WIDTH = 350;		// 리사이즈 될 가로크기
	private static final int IMG_HEIGHT = 350;		// 리사이즈 될 세로크기
	public static final int RATIO = 0;
    public static final int SAME = -1;

	// 실제 파일 삭제
	public static void doFileDelete(String fileName, String path) 
	        throws Exception {
		File file = null;
		if(StringUtils.isEmpty(fileName)) {
			fileName = "temp.jpg";
		}
		String fullFileName = path + fileName;
		logger.debug("파일 삭제를 시작 합니다.["+fullFileName+"]");
        file = new java.io.File(fullFileName);
        if (file.exists())
           file.delete();
	}	
	
	// path : 파일을 저장할 경로
	// 리턴 : 서버에 저장된 새로운 파일명
	public static void appFtpFileUpload(TAData serverInfo, String newFileName, String path, String mmsFileName, String imgType, byte[] decString, String gubun, String dbFileName, String imgGbnCd) throws Exception {
		
		if(logger.isDebugEnabled()) {
			logger.debug("FTP 이미지 업로드를 시작 합니다.");
		}
		
		if(StringUtils.isNotEmpty(dbFileName)) {
			// 서버에 저장할 새로운 파일명을 만든다.
			newFileName = dbFileName;
		}
		
		FTPClient client = null;
 		
		try {
			client = new FTPClient();
	 
			// 한글파일명 때문에 디폴트 인코딩을 euc-kr로 한다.
			client.setControlEncoding("euc-kr");
	 
			// 서버 정보
			logger.info("Commons NET FTP Client Test Program");
	 
			// 서버 접속
			client.connect((String)serverInfo.get("ip"));
			logger.info("Connected to...");
	 
			// 응답코드가 비정상일 경우 종료함
			int reply = client.getReplyCode();
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				logger.info("FTP server refused connection");
	 
			} else {
				logger.info(client.getReplyString());
	 
				// timeout을 설정
				client.setSoTimeout(10000);
				
				// 서버 로그인
				client.login((String)serverInfo.get("id"), (String)serverInfo.get("pwd"));
				logger.info("Login success...");
	 
				logger.debug("client.isConnected() ="+client.isConnected());
				logger.debug("client.isAvailable() ="+client.isAvailable());
				logger.debug("client.isRemoteVerificationEnabled() ="+client.isRemoteVerificationEnabled());
								
				// 파일 형식 설정(.BINARY_FILE_TYPE)
				client.setFileType(FTP.BINARY_FILE_TYPE);
				
				ByteArrayInputStream in = null;
				ByteArrayOutputStream os = null;
				InputStream is = null;
				try {
					// 카드 이미지는 MMS용 이미지를 만들어야 하기 때문에 resize를 해야함.
					if(StringUtils.equals(imgGbnCd, "10")) {
						in = new ByteArrayInputStream(decString);
						BufferedImage originalImage = ImageIO.read(in);
						
						int type = BufferedImage.TYPE_INT_RGB;
						int realImgSize = decString.length;
						int realImgWidth = originalImage.getWidth();
						int realImgHeight = originalImage.getHeight();
						
						int resizeImgSize = decString.length;;
						int resizeImgWidth = originalImage.getWidth();
						int resizeImgHeight = originalImage.getHeight();
						
						int maxImgSize = 102400;
						
						if(realImgSize > maxImgSize) {
						
							logger.info("realImgSize = "+realImgSize);
							logger.info("realImgWidth = "+realImgWidth);
							logger.info("realImgHeight = "+realImgHeight);
							
							resizeImgSize = 0;
							resizeImgWidth = 0;
							resizeImgHeight = 0;
							
							int addImgSize = 0;
							int addImgWidth = 0;
							int addImgHeight = 0;
							
							Double size = (realImgSize * 0.01);
							addImgSize = size.intValue();
							
							Double width = (realImgWidth * 0.01);
							addImgWidth = width.intValue();
							
							Double height = (realImgHeight * 0.01);
							addImgHeight = height.intValue();
							
							logger.info("addImgSize = "+addImgSize);
							logger.info("addImgWidth = "+addImgWidth);
							logger.info("addImgHeight = "+addImgHeight);
							
							while(resizeImgSize <= maxImgSize) {
								
								resizeImgSize += addImgSize;
								
								if(resizeImgWidth < 1000) {
									resizeImgWidth += addImgWidth;
								}
								if(resizeImgHeight < 1000) {
									resizeImgHeight += addImgHeight;
								}
							}
							
							resizeImgSize -= addImgSize;
							resizeImgWidth -= addImgWidth;
							resizeImgHeight -= addImgHeight;
							
							
							logger.info("resizeImgSize = "+resizeImgSize);
							logger.info("resizeImgWidth = "+resizeImgWidth);
							logger.info("resizeImgHeight = "+resizeImgHeight);
						
						}

						BufferedImage resizeImageJpg = imageResize(originalImage, resizeImgWidth, resizeImgHeight, type);
						
						// 바이트 처리
						os = new ByteArrayOutputStream();
						ImageIO.write(resizeImageJpg, "jpg", os);
						is = new ByteArrayInputStream(os.toByteArray());
						
						String imgSvrPath = (String)serverInfo.get("serverPath") + mmsFileName;
						logger.debug("업로드 경로 : " + imgSvrPath);
						boolean result = client.storeFile(imgSvrPath, is);
						logger.debug("업로드 결과 => "+result);
						
					}else {
						in = new ByteArrayInputStream(decString);
						String imgSvrPath = (String)serverInfo.get("serverPath") + newFileName;
						logger.debug("업로드 경로 : " + imgSvrPath);
						boolean result = client.storeFile(imgSvrPath, in);
						logger.debug("업로드 결과 => "+result);
					}
				}catch(Exception e) {
					logger.error("이미지 업로드 중 에러가 발생 하였습니다.", e);
					throw new Exception("이미지 업로드 중 에러가 발생 하였습니다.", e);
				}finally {
					if(in != null) {
						in.close();
					}
					if(os != null) {
						os.close();
					}
					if(is != null) {
						is.close();
					}
				}
	 
				client.logout();
			}
	 
		} catch (Exception e) {			
			logger.info("해당 ftp 로그인 실패하였습니다.");
			System.exit(-1);
		} finally {
			if(client != null && client.isConnected()){
				try {
					client.disconnect();
				}catch(IOException ioe) {
					logger.error("이미지 업로드 중 에러가 발생 하였습니다.");
					throw new Exception("이미지 업로드 중 에러가 발생 하였습니다.", ioe);
				}
			}
		}
	}
	
	// 이미지 리사이징
	// originalImage : 원본 이미지
	// type : 타입
	public static BufferedImage imageResize_back(BufferedImage originalImage, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
			
		return resizedImage;
	}
	
	// 이미지 리사이징(화질을 유지하는 리사이징)
	// originalImage : 원본 이미지
	// width : 가로 사이즈
	// height : 세로 사이즈
	// type : 타입
	public static BufferedImage imageResize(BufferedImage originalImage, int width, int height, int type) throws IOException {
		Image image = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		int pixels [] = new int[width * height];
		PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
		try {
			pixelGrabber.grabPixels();
		} catch (InterruptedException e) {
			logger.error("이미지 업로드 중 에러가 발생 하였습니다.");
			throw new IOException("이미지 업로드 중 에러가 발생 하였습니다.", e);
		}
		
		BufferedImage destImg = new BufferedImage(width, height, type);
		destImg.setRGB(0, 0, width, height, pixels, 0, width);
		
		return destImg;
	}
}
