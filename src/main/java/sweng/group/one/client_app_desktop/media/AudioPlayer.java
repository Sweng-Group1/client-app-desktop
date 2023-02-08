// published by Oli Partridge

package sweng.group.one.client_app_desktop.media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
// import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.JPanel;

// potentially replace with code made in the company, not sure
// if its been made or not yet
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

public class AudioPlayer extends JFrame{
	private static final String AUDIO_PATH = "where Ever Frasers folder is";
	private AudioPlayerComponent audioPlayerComponent;
	// Play and Pause Buttons will be combined 
	private JButton playAudioButton;
	private JButton pauseAudioButton;
	
	
	public AudioPlayer(String title) {
		super(title);
		
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
		playAudioButton = new JButton("image will replace this");
		pauseAudioButton = new JButton("image will replace this");
		
		//play and pause will overlap one another, depending on which has been clicked.
		playAudioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioPlayerComponent.mediaPlayer().controls().play();
			}
		});
		
		pauseAudioButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            audioPlayerComponent.mediaPlayer().controls().pause();
	         }
	      });
		this.setContentPane(audioControls);
		this.setVisible(true);
	}
	
	public void loadAudio(String path) {
		audioPlayerComponent.mediaPlayer().media().startPaused(path);
	}
	
	// some more stuff on loading files here:
	// https://www.tutorialspoint.com/vlcj/vlcj_audio_player.htm
	
			
}
