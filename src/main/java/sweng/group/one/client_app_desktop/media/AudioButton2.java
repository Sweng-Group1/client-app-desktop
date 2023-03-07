// published by Oli Partridge

package sweng.group.one.client_app_desktop.media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
// import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

// potentially replace with code made in the company, not sure
// if its been made or not yet
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

public class AudioButton extends PlayableMediaElement{
	//private static final String AUDIO_PATH = getLocalPath();
	private AudioPlayerComponent audioPlayerComponent;
	// Play and Pause Buttons will be combined 
	private JToggleButton AudioButton;
	
	JFrame window;
	
	public AudioPlayer(String title) {
		super(pos, width, height, duration, slide, fileURL);
		
		audioPlayerComponent = new AudioPlayerComponent();
		audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
	         @Override
	         public void finished(MediaPlayer mediaPlayer) {
	            System.out.println("Audio Playback Finished.");
	         }
	         @Override
	         public void error(MediaPlayer mediaPlayer) {
	            System.out.println("Failed to load Audio.");
	         }
		});
   }
// initialise audio player like speaker button in PP.
// needs to be sorted out and not just a button, will be picture instead.
	public void initialize() {
		this.setBounds(100, 100, 100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				audioPlayerComponent.release();
				System.exit(0);
			}
			
		});
		
		
		//Border audioBorder = BorderFactory.createTitledBorder("Audio Controls");
		JPanel audioControls = new JPanel();
		//audioControls.setBorder(audioBorder);
		Icon icon = new ImageIcon("client-app-desktop/assets/Speaker_Icon.png");
		
		AudioButton = new JToggleButton(icon);
		
		//play and pause will overlap one another, depending on which has been clicked.
		AudioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioPlayerComponent.mediaPlayer().controls().play();
			}
		});
		
		AudioButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            audioPlayerComponent.mediaPlayer().controls().pause();
	         }
	      });
		this.setContentPane(audioControls);
		this.setVisible(true);
	}
	
	public void loadAudio(String path) {
		audioPlayerComponent.mediaPlayer().media().startPaused(path);
		PlayableMediaElement
	}
	@Override
	public void togglePlaying() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean getPlaying() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void loadFile() {
		// TODO Auto-generated method stub
		
	}
	
	// class is based needs to be tendered towards the playable media element
	
	// some more stuff on loading files here:
	// https://www.tutorialspoint.com/vlcj/vlcj_audio_player.htm
	
			
}
