package VAC.ServicesImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.server.UID;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import VAC.Dto.NoticeDto;
import VAC.Entity.Notice;
import VAC.ErrorHandler.ResourceNotFound;
import VAC.Reposiotery.NoticeRepo;
import VAC.Services.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepo noticeRepo;

	// create Notice
	@Override
	public Notice createNotice(Notice notice) {

		Notice createdUser = this.noticeRepo.save(notice);

		return createdUser;
	}

	@Override 
	public Boolean createNotice(NoticeDto noticeDto) throws IOException {
		UID iUid = new UID();

		String uidString = iUid.toString().replace(':', '_');
		String originalFilename = noticeDto.getFile().getOriginalFilename();

		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex >= 0) {
			extension = originalFilename.substring(dotIndex);
			originalFilename = originalFilename.substring(0, dotIndex);
		}

		String imageName = originalFilename + "_" + uidString + extension;

		String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
		Path uploadPath = Paths.get(uploadDirectory);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Path imagePath = uploadPath.resolve(imageName);
		try (InputStream inputStream = noticeDto.getFile().getInputStream()) {
			Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + originalFilename, e);
		}

		Notice notice = new Notice();
		notice.setTitle(noticeDto.getTitle());
		notice.setDescription(noticeDto.getDescription());
		notice.setImageName("http://localhost:9191/" + imageName);
		notice.setNoticeDate(noticeDto.getNoticeDate());
		notice.setIsActive(false);

		Notice createdNotice = this.noticeRepo.save(notice);
//		try {
//			Thread.sleep(1500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		if (createdNotice instanceof Notice) {
			return true;
		}
		return false;

	}

	// updating notice using dto

	public Boolean updateNotice(String title, String description, String imageName, String noticeDate,
			MultipartFile file, Integer id) throws IOException {

		if (file != null) {

			UID iUid = new UID();

			String uidString = iUid.toString().replace(':', '_'); // replace ':' with '_'
			String originalFilename = file.getOriginalFilename();

			// Ensure that the original file name has an extension
			String extension = "";
			int dotIndex = originalFilename.lastIndexOf('.');
			if (dotIndex >= 0) {
				extension = originalFilename.substring(dotIndex);
				originalFilename = originalFilename.substring(0, dotIndex);
			}
			// for multipart
			String imageNames = originalFilename + "_" + uidString + extension;

			// Save the file to the server file system
			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";
			Path uploadPath = Paths.get(uploadDirectory);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);

			}

			Path imagePath = uploadPath.resolve(imageNames);
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException("Failed to store file " + originalFilename, e);
			} // Save the Notice in the database

			Notice updateNotice = this.noticeRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFound("Notice", "Notice id", id));

			String deletePhoto = updateNotice.getImageName().replace("http://localhost:9191", "");
			System.out.println(deletePhoto);
			Path filePath = Paths.get(uploadDirectory, deletePhoto);
			Files.deleteIfExists(filePath);

			updateNotice.setTitle(title);
			updateNotice.setNoticeDate(noticeDate);

			updateNotice.setDescription(description);
			updateNotice.setImageName("http://localhost:9191/" + imageNames);
			updateNotice.setIsActive(false);

			Notice notice = this.noticeRepo.save(updateNotice);
//			try {
//				Thread.sleep(1500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (notice instanceof Notice) {
				return true;
			}
			return false;

		} else {
			try {
				Notice updateNotice = this.noticeRepo.findById(id)
						.orElseThrow(() -> new ResourceNotFound("Notice", "Notice id", id));
				updateNotice.setTitle(title);
				updateNotice.setNoticeDate(noticeDate);

				updateNotice.setDescription(description);
				updateNotice.setImageName(imageName);
				updateNotice.setIsActive(false);

				Notice notice = this.noticeRepo.save(updateNotice);
				if (notice instanceof Notice) {
					return true;
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;

			}
		}

	}

	// use the isActive
	public Boolean updateNoticeIsActive(Integer id) {
		try {
			Notice updateNotice = this.noticeRepo.findById(id)
					.orElseThrow(() -> new ResourceNotFound("Notice", "Notice id", id));

			updateNotice.setIsActive(!updateNotice.getIsActive());

			Notice savedNotice = this.noticeRepo.save(updateNotice);

			if (savedNotice instanceof Notice) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;

	}

	// get all notice
	@Override
	public List<Notice> getAllNotice() {

		List<Notice> allNotice = this.noticeRepo.findAll();

		return allNotice;

	}

	// get specific notice
	@Override
	public Optional<Notice> getNoticeById(Integer id) {

		try {

			return this.noticeRepo.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// delete notice..
	@Override
	public Boolean deleteNotice(Integer id) {

		Notice deleteNotice = this.noticeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Notice", "Notice id", id));
		try {
			// deleting file in project folder tooo
			String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";

			String deletePhoto = deleteNotice.getImageName().replace("http://localhost:9191", "");
			System.out.println(deletePhoto);
			Path filePath = Paths.get(uploadDirectory, deletePhoto);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.noticeRepo.delete(deleteNotice);
		Optional<Notice> deletedNotice = this.noticeRepo.findById(id);

		if (deletedNotice.isPresent()) {
			return false;
		}

		return true;
	}

}
