package TestProject.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import TestProject.GameDesktop;

public class DesktopIconActions {
	public class CreateProfileButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			CharacterCreationInterface creator = new CharacterCreationInterface(null);
			GameDesktop.Desktop.add(creator);
		}
		
	}
}
