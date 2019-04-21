package notebox;

public class NoteBoxDTO {
	
	private String toUserID;
	private String fromUserID;
	private String noteTitle;
	private String noteContent;
	private int noteBoxID;
	private String evaluationID;
	public String getEvaluationID() {
		return evaluationID;
	}
	public void setEvaluationID(String evaluationID) {
		this.evaluationID = evaluationID;
	}
	public String getToUserID() {
		return toUserID;
	}
	public void setToUserID(String toUserID) {
		this.toUserID = toUserID;
	}
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public int getNoteBoxID() {
		return noteBoxID;
	}
	public void setNoteBoxID(int noteBoxID) {
		this.noteBoxID = noteBoxID;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	
	public NoteBoxDTO() {
		
	}
	
	public NoteBoxDTO(String toUserID, String fromUserID, String noteTitle, String noteContent, int noteBoxID, String evaluationID) {
		super();
		this.toUserID = toUserID;
		this.fromUserID = fromUserID;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteBoxID = noteBoxID;
		this.evaluationID = evaluationID;
	}
	
	
}
