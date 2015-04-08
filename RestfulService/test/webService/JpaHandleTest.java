package webService;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class JpaHandleTest {

	@Test
	public void testGetTitle() {
		List<String> titleList = JpaHandle.getTitle();
		assertSame(3, titleList.size());
	}

}
